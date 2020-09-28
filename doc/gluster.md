# GlusterFS

## Requirements
- One node(Virtual machine or phisical) or more
- Virtual or phisical disk.
- Network connection.

### Format and mount the disks
```
bash# mkfs.xfs -i size=512 /dev/sdb1
bash# mkdir -p /media/external120
```
### Install glusterfs on fedora, centos or ubuntu
```
bash# apt install glusterfs-server (yum install for fedora/centos)
bash# service glusterd start
bash# service glusterd status
  ● glusterd.service - GlusterFS, a clustered file-system server
     Loaded: loaded (/lib/systemd/system/glusterd.service; enabled; vendor pres>
     Active: active (running) since Thu 2020-09-17 15:04:30 UTC; 1 weeks 2 days>
       Docs: man:glusterd(8)
   Main PID: 1295 (glusterd)
      Tasks: 114 (limit: 972)
     CGroup: /system.slice/glusterd.service

```
### Set up a GlusterFS volume
Create the folder for the volume
```
bash# mkdir -p /media/external120/elastictranscoder-data
```
From any single server
```
bash# gluster volume create elastictranscoder-data replica 1 server1:/media/external120/elastictranscoder-data
volume create: elastictranscoder-data: success: please start the volume to access data
bash# gluster volume start elastictranscoder-data
volume start: elastictranscoder-data: success
```
Confirm that the volume shows "Started":
```
# gluster volume info
```
You should see something like this (the Volume ID will differ): 
```
Volume Name: elastictranscoder-data
Type: Distribute
Volume ID: aa0c1a54-6227-4710-b535-9400f74b7c00
Status: Started
Snapshot Count: 0
Number of Bricks: 1
Transport-type: tcp
Bricks:
Brick1: raspberrypi3:/media/external120/elastictranscoder-data
Options Reconfigured:
transport.address-family: inet
storage.fips-mode-rchecksum: on
nfs.disable: on

```
Now gluster is ready for use on elastictranscoder.