const std = @import("std");
const io = std.io;
const os = std.os;
const mem = std.mem;

const caseDifference = 'a' - 'A';

pub fn toLower(c: u8) u8 {
    if (c <= 'Z') {
        return c + caseDifference;
    }
    return c;
}

pub fn destructivePair(x: u8, y: u8) bool {
    return (x + 32 == y) or (x == y + 32);
}

// pub fn countRemaining(list: *std.LinkedList(u8)) usize {
//     var done = false;
//     while (!done) {
//         done = true;
//         var it = list.first;
//         while (it) |node| {
//             if (node.next) |next| {
//                 if (destructivePair(node.data, next.data)) {
//                     list.remove(node);
//                     list.remove(next);
//                     done = false;
//                     it = node.next;
//                 }
//             } 
//             if (it) |n| {
//                 it = n.next;
//             }
//         }
//     }
//     return list.len;
// }

pub fn countRemaining(alloc: *mem.Allocator, list: *const std.LinkedList(u8), c: ?u8) !usize {
    var compact = std.LinkedList(u8).init();
    
    var it = list.first;
    while (it) |node| : (it = node.next) {
        if (c) |x|
            if (toLower(node.data) == x)  continue;
        if (compact.last) |last| {
            if (destructivePair(last.data, node.data)) {
                compact.remove(last);
            } else {
                var n = try compact.createNode(node.data, alloc);
                compact.append(n);
            }
        } else {
            var n = try compact.createNode(node.data, alloc);
            compact.append(n);
        }
    }
    return compact.len;
}

pub fn main() !void {
    var stdout_file = try io.getStdOut();
    const stdout = &stdout_file.outStream().stream;

    var input_file = try os.File.openRead("../resources/day5");
    var input_stream = input_file.inStream();

    var direct_allocator = std.heap.DirectAllocator.init();
    const alloc = &direct_allocator.allocator;

    var buf = try std.Buffer.initSize(alloc, 0);

    var input = try io.readLineFrom(&input_stream.stream, &buf);

    var list = std.LinkedList(u8).init();

    for (input) |x| {
        var n = try list.createNode(x, alloc);
        // defer list.destroyNode(n, alloc);
        list.append(n);
    }

    try stdout.print("{}\n", try countRemaining(alloc, &list, null));

    var min: usize = std.math.maxInt(usize);
    var c: u8 = 'a';
    while (c <= 'z') : (c += 1) {
        min = std.math.min(min, try countRemaining(alloc, &list, c));
    }

    try stdout.print("{}\n", min);

}
