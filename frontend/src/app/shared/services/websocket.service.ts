import { TranscodeMedia } from './../models/transcodemedia.model';
import { environment } from './../../../environments/environment';
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import { Injectable } from '@angular/core';
import { Subject, Observable, BehaviorSubject } from 'rxjs';

@Injectable()
export class WebSocketService {

    public stompClient;
    private transcodeStatus = new Subject<TranscodeMedia[]>();
    public transcodeStatusObs = this.transcodeStatus.asObservable();

    constructor() {
        this.initializeWebSocketConnection();
    }

    initializeWebSocketConnection() {
        const serverUrl = environment.apiUrl + 'status';
        const ws = new SockJS(serverUrl);
        this.stompClient = Stomp.over(ws);
        this.stompClient.debug = () => { };
        const that = this;
        this.stompClient.connect({}, function (frame) {
            that.stompClient.subscribe('/transcodes', (message) => {
                if (message.body) {
                    const jsonParsed: TranscodeMedia[] = JSON.parse(message.body);
                    that.updateObservable(jsonParsed);
                }
            });
        });
    }

    updateObservable(data: TranscodeMedia[]) {
        this.transcodeStatus.next(data);
    }

    sendMessage(message) {
        this.stompClient.send('/app/send/message', {}, message);
    }
}