#!/bin/env python
import  sys, re


class Processor:
    def __init__(self, reader, writer):
        self.reader =  open(reader, 'r')
        self.writer =  open(writer, 'w')


    def convert(self, data):
        assert 0, 'convert needed to be defined'


    def process(self):
        while 1:
            line = self.reader.readline()
            if not line:  break
            data = self.convert(line)
            self.writer.write(data)




class UpperCase(Processor):
    def convert(self, data):
        return data.upper()


class HTMLize:
    def write(self, data):
        print  '%s' % data[:-1]


class ScanApacheLog(Processor):
    def convert(self, data):
        p = re.compile(r"""
        (?P\S+) #remote_host
        \s+ #whitespace
        \S+ #remote_login
        \s+ #whitespace
        \S+ #remote_user
        \s+ #whitespace
        (?P\[.*\]) #acess_time
        \s+ #whitespace
        (?P".*") #first line of request
        \s+ #whitespace
        (?P\d+) #status
        \s+ #whitespace
        (-|\d+) # send_size
        \s*
        """, re.X)
        m = p.match(data)
        if m is not None:
            arr = m.groupdict()
            request = re.match( r'"(.*)"', arr['request']).group(1)
            access_time = re.match(r'\[(\S+)\s+\S+\]', arr['time']).group(1)
            line = arr['remote_host'] + ','  + access_time + ',' +  request + ',' +  arr['status'] + '\n'
            return line


if  __name__ == '__main__':
    UpperCase(open('1.txt'), sys.stdout).process()
    UpperCase(open('1.txt'), open('2.txt', 'w')).process()
    UpperCase(open('1.txt'), HTMLize()).process()
