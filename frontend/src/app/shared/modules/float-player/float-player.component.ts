import { Component, OnInit, Input, Inject, ElementRef, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { DomSanitizer } from '@angular/platform-browser';
import {
    BitrateOptions,
    VgApiService,
} from '@videogular/ngx-videogular/core';
import { VgHlsDirective } from '@videogular/ngx-videogular/streaming';
@Component({
    selector: 'app-float-player',
    templateUrl: './float-player.component.html',
    styleUrls: ['./float-player.component.scss']
})
export class FloatPlayerComponent implements OnInit {
    @ViewChild('target', { static: true }) target: ElementRef;

    safeUrl: any;


    constructor(private elementRef: ElementRef,
        private dialogRef: MatDialogRef<FloatPlayerComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private _sanitizer: DomSanitizer
    ) {
        // this.bookmark = data.bookmark;
        //demo m3u8  https://s3-eu-west-1.amazonaws.com/s3.lavandadelpatio/hls-v2/prog_index.m3u8
        // this.safeUrl = this._sanitizer.bypassSecurityTrustResourceUrl(data.bookmark);
    }
    playPause(event: UIEvent) {
        event.preventDefault();
    }

    ngOnInit() {
        this.safeUrl = this._sanitizer.bypassSecurityTrustResourceUrl(this.data.url);

    }
    close() {
        this.dialogRef.close('Closed!');
    }

}
