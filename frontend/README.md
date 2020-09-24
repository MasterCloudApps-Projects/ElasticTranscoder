<p align="center">
  <img src="https://github.com/luiscajl/VideoTranscoding-Frontend/blob/master/src/assets/images/VideoTranscoding_logo.png"/>
</p>

[![Build Status](https://travis-ci.org/luiscajl/VideoTranscoding-Frontend.svg?branch=master)](https://travis-ci.org/luiscajl/VideoTranscoding-Frontend)
![Version](https://img.shields.io/badge/version-1.0-brightgreen.svg?style=flat)
[![License badge](https://img.shields.io/badge/license-Apache2-orange.svg)](http://www.apache.org/licenses/LICENSE-2.0)

This application transcode a video that you send on all formats what you want and diferent resolutions. It´s build on a docker container to simplify the installation.
## Test it:
1. Visit [Urjc VideoTranscoding](https://urjc.videotranscoding.es) and create and new user.
2. You can upload a video an transform whatever you want.
3. Download your video converted when it finished.
## Run it:
1. Install [docker](https://docs.docker.com/engine/installation/) for your machine.
2. Download [the docker-compose.yml](https://raw.githubusercontent.com/luiscajl/VideoTranscoding-Frontend/master/docker-compose.yml).
3. Run this command on your terminal on the path of docker-compose.
```sh
sudo docker-compose up 
```
4. Visit [localhost](http://localhost:8877/) on your web browser.
5. Stop docker compose when you finished
```sh
ctrl+c
```


## Develop it on Frontend ( needs the [backend](https://github.com/luiscajl/VideoTranscoding-Backend/)):
1. Clone respository:
```sh
git clone https://github.com/luiscajl/VideoTranscoding-Frontend.git 
```
2. Install [Node](https://nodejs.org/es/download/).
3. On the folder project do:
```sh
npm i 
```
4. And:
```sh
ng serve 
```
5. Visit [http://localhost:4000](http://localhost:4000):


The project is finished and ready to deliver to my tutor

## Screenshots:
<p align="center">
  <img src="https://github.com/luiscajl/VideoTranscoding-Frontend/blob/master/screens/login.png"/>
</p>
<p align="center">
  <img src="https://github.com/luiscajl/VideoTranscoding-Frontend/blob/master/screens/register.png"/>
</p>
<p align="center">
  <img src="https://github.com/luiscajl/VideoTranscoding-Frontend/blob/master/screens/dashboard.png"/>
</p>
<p align="center">
  <img src="https://github.com/luiscajl/VideoTranscoding-Frontend/blob/master/screens/dashboard2.png"/>
</p>
<p align="center">
  <img src="https://github.com/luiscajl/VideoTranscoding-Frontend/blob/master/screens/uploadVideos.png"/>
</p>
<p align="center">
  <img src="https://github.com/luiscajl/VideoTranscoding-Frontend/blob/master/screens/manageVideos.png"/>
</p>
<p align="center">
  <img src="https://github.com/luiscajl/VideoTranscoding-Frontend/blob/master/screens/player1.png"/>
</p>
<p align="center">
  <img src="https://github.com/luiscajl/VideoTranscoding-Frontend/blob/master/screens/player2.png"/>
</p>

