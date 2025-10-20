set val(chan) Channel/WirelessChannel; #Create a wireless channel
set val(prop) Propagation/TwoRayGround; # Create a propagation model
set val(netif) Phy/WirelessPhy; # Create a wireless inteface
set val(mac) Mac/802_11;
set val(ifq) Queue/DropTail/PriQueue;
set val(ll) LL;
set val(ant) Antenna/OmniAntenna;
set val(ifqlen) 50;
set val(rp) AODV;
set val(nn) 10; # Set number of nodes 
set val(x) 500;
set val(y) 500;
set val(stop) 1000; # Set simulation time to 1000 seconds

# Create a new simulator object
set ns [new Simulator]

# Open the NAM trace file
set nf [open out.nam w]
$ns namtrace-all-wireless $nf $val(x) $val(y)

# Open the Trace file
set tf [open out.tr w]
$ns trace-all $tf

$ns use-newtrace
# Create a flat grid topography whithin a topology 500m*500m boundary
set topo [new Topography]
$topo load_flatgrid $val(x) $val(y)

# Create god and store the totsl number of mobilenodes
create-god $val(nn)

# Create a wireless channel
set chan_1_ [new $val(chan)]

# Configure node properties
$ns node-config -adhocRouting $val(rp) \
 -llType $val(ll) \
 -macType $val(mac) \
 -ifqType $val(ifq) \
 -ifqLen $val(ifqlen) \
 -antType $val(ant) \
 -propType $val(prop) \
 -phyType $val(netif) \
 -channel $chan_1_ \
 -topoInstance $topo \
 -agentTrace ON \
 -routerTrace ON \
 -macTrace OFF \
 -movementTrace ON \
 -rxPower 0.4 \
 -txPower 1.0 \
 -idlePower 0.6 \
 -sleepPower 0.1 \
 -transitionPower 0.4 \
 -transitionTime 0.1

# Create wireless nodes and set their positions
for {set i 0} {$i < $val(nn)} {incr i} {
    set node_($i) [$ns node]
    set angle [expr $i*(360/$val(nn))]
    $node_($i) set X_ [expr 200 + 200 * cos($angle*3.14159/180.0)]
    $node_($i) set Y_ [expr 200 + 200 * sin($angle*3.14159/180.0)]
    $node_($i) set Z_ 0.0
}

# Create and configure agents for communication
# Setup traffic flow using UDP
for {set i 0} {$i < [ expr round($val(nn)*0.1) ] } {incr i} {
	set udp [new Agent/UDP]
	set null [new Agent/Null]
	#$udp set class_ 1
	$ns attach-agent $node_(3) $udp
	$ns attach-agent $node_(9) $null
	$ns connect $udp $null
	set cbr [new Application/Traffic/CBR]
	$cbr attach-agent $udp
	$cbr set packetSize_ 512
	$cbr set interval_ 0.1
	$cbr set rate_ 1mb
	#$cbr set maxpkts_ 10000

	$ns at 0.4 "$cbr start"
}

# Using TCP
for {set i  [ expr round($val(nn)*0.2) ] } {$i < [ expr round($val(nn)*0.4) ] } {incr i} {
	set tcp [new Agent/TCP]
	#$tcp set class_ 2
	set sink [new Agent/TCPSink]
	$ns attach-agent $node_(1) $tcp
	$ns attach-agent $node_(5) $sink
	$ns connect $tcp $sink
	set ftp [new Application/FTP]
	$ftp attach-agent $tcp
	$ns at 0.4 "$ftp start"
}


# Set initial positions of nodes
for {set i 0} {$i < $val(nn)} {incr i} {
        $ns initial_node_pos $node_($i) 30
}

# Schedule reset action for each node at stop time
for {set i 0} {$i < $val(nn)} {incr i} {
	$ns at $val(stop) "$node_($i) reset";
}

# Schedule end of nam trace and finish procedure
$ns at $val(stop) "$ns nam-end-wireless $val(stop)"
$ns at $val(stop) "finish"
$ns at $val(stop)+0.1  "puts \"end simulation\"; $ns halt"

# Procedure to finish the simulation
proc finish {} {
        global ns tf nf
        $ns flush-trace
        close $tf
        close $nf
        exec nam out.nam &
        exit 0
}

# Print CBR parameters
puts "CBR packet size = [$cbr set packetSize_]"
puts "CBR interval = [$cbr set interval_]"

# Run the simulation:
$ns run
