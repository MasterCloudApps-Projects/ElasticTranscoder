# [ElasticTranscoder](https://videotranscoding.es)
![Frontend](https://github.com/MasterCloudApps-Projects/ElasticTranscoder/workflows/Build%20docker%20frontend/badge.svg)
![Transcoder](https://github.com/MasterCloudApps-Projects/ElasticTranscoder/workflows/Build%20docker%20transcoder/badge.svg)
![Transcoder Status](https://github.com/MasterCloudApps-Projects/ElasticTranscoder/workflows/Build%20docker%20transcoder-status/badge.svg)
![Transcoder Handler](https://github.com/MasterCloudApps-Projects/ElasticTranscoder/workflows/Build%20docker%20transcoder-handler/badge.svg)
![Media Storage](https://github.com/MasterCloudApps-Projects/ElasticTranscoder/workflows/Build%20docker%20media-storage/badge.svg)
![Media](https://github.com/MasterCloudApps-Projects/ElasticTranscoder/workflows/Build%20docker%20media/badge.svg)


## Elastic application for transcode videos using FFMPEG
### Architecture and components
![](doc/img/ElasticTranscoder.png)
- Frontend use Angular 10 and material.
- Transcoder: Springboot application (JDK 13) which read a SQS queue to transcode the video
- Media-Storage: 
- Media:
- Transcoder-Status:
- Transcoder-Handler:

All microservices, including frontend, used [OKTA](https://www.okta.com/) to secured it. Please, [read here](doc/okta.md) to configure it for this proyect.

The glusterFS cluster is optional, but mandatory if you want externalise the filesystem. [Read here](doc/gluster.md) to configure it.

For configure AWS SQS and DynamoDB use [this](doc/aws.md).
### Deploy it:
Need [HELM V3](https://helm.sh/docs/intro/install/) on your cluster.
```
helm add repo 
```
####


### Next release:

* Support HLS transcode
* Support audio transcode (video--> to only audio , audio --> audio)
* Fix delete-media flow (parent folders not deleted)
* Fix transcoder for support speed, filesize and bitrate
* Fix transcoder-handler to support threads, select audio bitrate and CRF (VideoTranscodeServiceImpl:108)