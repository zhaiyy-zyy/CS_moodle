load CW.hdl,
output-file T1.out,
compare-to T1.cmp,
output-list time%S1.4.1 inID%D3.6.3 inMARK%D3.6.3 load%B3.1.3 probe%B3.1.3 address%D3.1.3 sl%B3.1.3 priority%D3.1.3 outID%D1.6.1 outMARK%D1.6.1 sum%D1.6.1 avg%D1.6.1 overflow%B3.1.3;

set inID 12345,
set inMARK 97,
set load 1,
set probe 0,
set address 2,
set sl 0,
set priority 0,
tick,
output;

tock,
output;

set inID 0,
set inMARK 0,
set load 0,
set probe 1,
set address 2,
set sl 0,
set priority 0,
tick,
output;

tock,
output;



set inID 11111,
set inMARK 999,
set load 1,
set probe 0,
set address 2,
set sl 0,
set priority 0,
tick,
output;

tock,
output;

set inID 0,
set inMARK 0,
set load 0,
set probe 1,
set address 2,
set sl 0,
set priority 0,
tick,
output;

tock,
output;
 


set inID 22222,
set inMARK 32767,
set load 1,
set probe 0,
set address 1,
set sl 0,
set priority 0,
tick,
output;

tock,
output;

set inID 0,
set inMARK 0,
set load 0,
set probe 1,
set address 1,
set sl 0,
set priority 0,
tick,
output;

tock,
output;
 

set inID 32767,
set inMARK 111,
set load 1,
set probe 0,
set address 0,
set sl 0,
set priority 0,
tick,
output;

tock,
output;

set inID 0,
set inMARK 0,
set load 0,
set probe 1,
set address 1,
set sl 0,
set priority 0,
tick,
output;

tock,
output;
 

set inID -32767,
set inMARK 1767,
set load 1,
set probe 0,
set address 3,
set sl 0,
set priority 0,
tick,
output;

tock,
output;

set inID 0,
set inMARK 0,
set load 0,
set probe 1,
set address 3,
set sl 0,
set priority 0,
tick,
output;

tock,
output;
 

set inID 0,
set inMARK 0,
set load 0,
set probe 1,
set address 2,
set sl 0,
set priority 0,
tick,
output;

tock,
output;


set inID 0,
set inMARK 0,
set load 0,
set probe 1,
set address 1,
set sl 0,
set priority 0,
tick,
output;

tock,
output;
 
set inID 0,
set inMARK 0,
set load 0,
set probe 1,
set address 0,
set sl 0,
set priority 0,
tick,
output;

tock,
output;
 