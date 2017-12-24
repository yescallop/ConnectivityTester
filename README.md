# ConnectivityTester
A Java HTTP/HTTPS connectivity tester based on *Apache HttpClient*

# Usage:
    1. Create a file named "ips.txt" in the same folder as the jar
    2. Put the url in the first line, then put each IP address of the host in new lines
    3. Run the jar, input the number of times you want to test each IP address, then press enter

# Example of ips.txt
```
    https://steamcommunity.com/
    118.214.249.13
    23.10.6.47
    118.215.176.59
```

# Feature
Uses keep-alive connections, connecting speed can be intuitively seen

# Opreating Result
The numbers after each IP address are delays in *milliseconds*
```
Times: 10
https://steamcommunity.com/
118.214.249.13 1063 362 331 325 321 329 337 360 325 323
23.10.6.47 667 339 350 338 333 346 341 346 332 342
118.215.176.59 727 363 353 377 354 380 376 359 362 371
```
