#!/usr/bin/perl

use warnings;
use strict;

use POSIX;

use Cell;

print "Life simulator\n\n";

my $grid_size = 10;
my @grid = ([(0) x $grid_size], [(0) x $grid_size]);

foreach(1..$grid_size) {
    my $x_position = int(rand($grid_size - 1));
    my $y_position = int(rand($grid_size - 1));

    print $x_position . ", " . $y_position . " - ";

    my $cell = new Cell();

    $grid[$x_position][$y_position] = \$cell;
}

print "\n\n";

tick(\@grid, \$grid_size);





sub tick {
    my $arr = shift;
    my $grid_size = shift;

    foreach(my $i = 0; $i < $$grid_size; $i++) {
        foreach(my $j = 0; $j < $$grid_size; $j++) {
            if (defined $$arr[$i][$j] and ref($$arr[$i][$j])) {
                #print $$arr[$i][$j];
                my $obj = bless(\$$arr[$i][$j], 'Cell');
                print $obj->status;
            } else {
                print "0";
            }
            print " ";
        }
        print "\n";
    }
}

1;


