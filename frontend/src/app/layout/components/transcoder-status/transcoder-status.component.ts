import { WebSocketService } from './../../../shared/services/websocket.service';
import { MediaService } from './../../../shared/services/media.service';
import { TranscodeMedia } from './../../../shared/models/transcodemedia.model';
import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource, MatTable } from '@angular/material/table';

const initialSelection = [];
const allowMultiSelect = true;
@Component({
    selector: 'app-transcoder-status',
    templateUrl: './transcoder-status.component.html',
    styleUrls: ['./transcoder-status.component.scss']
})
export class TranscoderStatusComponent implements OnInit {
    displayedColumns: string[] = ['name', 'type',
        'filesize', 'container', 'videoCodec', 'audioCodec', 'resolution', 'bitrate', 'preset', 'processed'];
    transcodes: TranscodeMedia[] = [];

    constructor(private readonly webSocketService: WebSocketService, private mediaService: MediaService) { }

    ngOnInit() {
        // TODO: control de observables
        this.subscribedWebsocket();
    }

    private subscribedWebsocket() {
        this.webSocketService.transcodeStatusObs.subscribe((data: TranscodeMedia[]) => {
            this.transcodes = data;
        });
    }

}
