## JXLS
This library is what my team at office currently use. It is very convenience in terms of templating(styling and formatting in Excel).
It hides the complexity of apache poi just by providing template that you want.

All result here is deployed on local docker with 0.2 cpu
and 512Mb memory. So basically there will be 2 API that exports data into Excel(.xlsx) file. One of them return byte 
array as a result and the other is directly put into output stream.

In jxls you can enable or disable streaming option. Therefore, for this I made 4 testcase which are:

- Return Byte Array with streaming OFF
- Return Byte Array with streaming ON
- Directly put to output stream with streaming OFF
- Directly put to output stream with streaming ON

The result is presented below:

|              Approach             |             Row Count            | Start Time | End Time | Response Time |  Status |                                          Notes                                         |
|:---------------------------------:|:--------------------------------:|:----------:|:--------:|:-------------:|:-------:|:--------------------------------------------------------------------------------------:|
| Byte   Array - JXLS Streaming OFF |                           1.000  |   2.05.40  |  2.06.38 |    0.00.58    | SUCCESS |                                                                                        |
| Byte   Array - JXLS Streaming OFF |                          10.000  |   2.16.43  |  2.18.02 |    0.01.19    | SUCCESS |                                                                                        |
| Byte   Array - JXLS Streaming OFF |                         100.000  |   2.34.10  |  2.40.49 |    0.06.39    |  FAILED | java.util.concurrent.ExecutionException:   java.lang.OutOfMemoryError: Java heap space |
| Byte   Array - JXLS Streaming OFF |                       1.000.000  |   0.00.00  |  0.00.00 |    0.00.00    |  FAILED |                                                                                        |
| Byte   Array - JXLS Streaming OFF |                     100.000.000  |   0.00.00  |  0.00.00 |    0.00.00    |  FAILED |                                                                                        |
| OutStream - JXLS Streaming OFF    |                           1.000  |   3.26.42  |  3.27.32 |    0.00.50    | SUCCESS |                                                                                        |
| OutStream - JXLS Streaming OFF    |                          10.000  |   3.32.58  |  3.34.09 |    0.01.11    | SUCCESS |                                                                                        |
| OutStream - JXLS Streaming OFF    |                         100.000  |   3.35.59  |  3.52.01 |    0.16.02    |  FAILED | java.util.concurrent.ExecutionException:   java.lang.OutOfMemoryError: Java heap space |
| OutStream - JXLS Streaming OFF    |                       1.000.000  |   0.00.00  |  0.00.00 |    0.00.00    |  FAILED |                                                                                        |
| OutStream - JXLS Streaming OFF    |                     100.000.000  |   0.00.00  |  0.00.00 |    0.00.00    |  FAILED |                                                                                        |
| Byte Array - JXLS Streaming ON    |                           1.000  |   6.50.32  |  6.51.25 |    0.00.53    | SUCCESS |                                                                                        |
| Byte Array - JXLS Streaming ON    |                          10.000  |   6.52.09  |  6.52.52 |    0.00.43    | SUCCESS |                                                                                        |
| Byte Array - JXLS Streaming ON    |                         100.000  |   6.50.02  |  6.59.46 |    0.09.44    |  FAILED | java.util.concurrent.ExecutionException:   java.lang.OutOfMemoryError: Java heap space |
| Byte Array - JXLS Streaming ON    |                       1.000.000  |   0.00.00  |  0.00.00 |    0.00.00    |  FAILED |                                                                                        |
| Byte Array - JXLS Streaming ON    |                     100.000.000  |   0.00.00  |  0.00.00 |    0.00.00    |  FAILED |                                                                                        |
| OutStream - JXLS Streaming ON     |                           1.000  |   7.04.07  |  7.04.20 |    0.00.13    | SUCCESS |                                                                                        |
| OutStream - JXLS Streaming ON     |                          10.000  |   7.05.19  |  7.05.36 |    0.00.17    | SUCCESS |                                                                                        |
| OutStream - JXLS Streaming ON     |                         100.000  |   7.06.23  |  7.11.08 |    0.04.45    | SUCCESS |                                                                                        |
| OutStream - JXLS Streaming ON     |                       1.000.000  |   7.19.10  |  7.24.57 |    0.05.47    |  FAILED | java.util.concurrent.ExecutionException:   java.lang.OutOfMemoryError: Java heap space |
| OutStream - JXLS Streaming ON     |                     100.000.000  |   0.00.00  |  0.00.00 |    0.00.00    |  FAILED |                                                                                        |

By the result above, with the streaming ON the response time is relatively faster.
But still in terms of user experiences it's still too long. There's a lot of testcase and capability that can be explored, such as:

- Different style of templating
- Upgrade CPU and Memory
- Async produced link when file processing is too large.