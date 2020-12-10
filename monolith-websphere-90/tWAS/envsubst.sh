#!/bin/sh

mkdir /etc/websphere/orig
cp /etc/websphere/*.props /etc/websphere/orig/
for f in $(find /etc/websphere/orig/ -regex '.*\.props'); 
do 
  envsubst < $f > "/etc/websphere/$(basename $f)"; 
done
