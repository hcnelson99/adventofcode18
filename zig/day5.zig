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

pub fn countRemaining(alloc: *mem.Allocator, buf: []u8, c: ?u8) !usize {
    var compact = std.ArrayList(u8).init(alloc);
    
    var i : usize = 0;
    while (i < buf.len) : (i += 1) {
        var x = buf[i];
        if (c) |f|
            if (toLower(x) == f)  continue;

        if (compact.count() > 0) {
            var last = compact.at(compact.count() - 1);
            if (destructivePair(last, x)) {
                _ = compact.pop();
            } else {
                try compact.append(x);
            }
        } else {
            try compact.append(x);
        }
    }
    return compact.count();
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


    try stdout.print("{}\n", try countRemaining(alloc, buf.toSlice(), null));

    var min: usize = std.math.maxInt(usize);
    var c: u8 = 'a';
    while (c <= 'z') : (c += 1) {
        min = std.math.min(min, try countRemaining(alloc, buf.toSlice(), c));
    }

    try stdout.print("{}\n", min);

}
