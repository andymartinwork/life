package Cell;

use strict;
use warnings;

sub new {
    my $class = shift;
    my $self = {
        status => 1,
    };
    #print "Cell initilised";
    bless $self, $class;
    return $self;
}

sub status {
    my $self = shift;
    #print "status";

    return 1; #$self->{status};
}

1;
