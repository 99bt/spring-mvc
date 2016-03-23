#!/bin/env python
from  processor import *

if __name__ == '__main__':
    reader='/usr/local/nginx/logs/access.log '
    writer='/usr/local/nginx/logs/data.log'
    ScanApacheLog(reader, writer ).process()
