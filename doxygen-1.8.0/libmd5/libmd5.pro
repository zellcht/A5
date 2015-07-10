#
# This file was generated from libmd5.pro.in on Fri Feb 24 16:02:34 CET 2012
#

TEMPLATE	= lib
CONFIG		= warn_on staticlib debug
HEADERS		= md5.h md5_loc.h
SOURCES		= md5.c
win32:INCLUDEPATH          += .
win32-g++:TMAKE_CFLAGS     += -D__CYGWIN__ -DALL_STATIC
DESTDIR                    =  ../lib
TARGET                     =  md5
OBJECTS_DIR                =  ../objects

TMAKE_MOC = /usr/bin/moc
