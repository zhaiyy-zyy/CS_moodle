load CW.hdl,
output-file T3.out,
compare-to T3.cmp,
output-list time%S1.4.1 inID%D3.6.3 inMARK%D3.6.3 load%B3.1.3 probe%B3.1.3 address%D3.1.3 sl%B3.1.3 priority%D3.1.3 outID%D1.6.1 outMARK%D1.6.1 sum%D1.6.1 avg%D1.6.1 overflow%B3.1.3;
 
set inID 225,
set inMARK 227,
set load 1,
set probe 0,
set address 0,
set sl 1,
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
set priority 1,
tick,
output;

tock,
output; 

set inID 12346,
set inMARK 79,
set load 1,
set probe 0,
set address 0,
set sl 1,
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
set priority 1,
tick,
output;

tock,
output; 


set inID 555,
set inMARK 8888,
set load 1,
set probe 0,
set address 0,
set sl 1,
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
set priority 1,
tick,
output;

tock,
output; 


set inID 11,
set inMARK 32767,
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
set address 0,
set sl 0,
set priority 1,
tick,
output;

tock,
output; 

set inID 121,
set inMARK 32111,
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
set address 0,
set sl 0,
set priority 1,
tick,
output;

tock,
output; 