## Fast Excel
For presenting the data we need a helper class to apply the style and format. What I don't like is, the result format will
always be considered 'Custom' when you open the generated Excel file. In terms of performance, I think this library can utilize 
multi threading when fill the data into the worksheet(not tried that yet). But be careful when utilizing multi threading approach,
especially for a case when data is sorted(thread synchronization). 

All result here is deployed on local docker with 0.2 cpu
and 512Mb memory. So basically there will be 2 API that exports data into Excel(.xlsx) file. One of them return byte
array as a result and the other is directly put into output stream.

For this library I made 2 testcase which are:

- Return Byte Array
- Directly put to output stream

The result is presented below:

## COMING SOON!