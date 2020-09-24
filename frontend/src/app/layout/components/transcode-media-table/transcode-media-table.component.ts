import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { FloatPlayerComponent } from './../../../shared/modules/float-player/float-player.component';
import { DialogDeleteMediaComponent } from './../dialog-delete-media/dialog-delete-media.component';
import { MediaService } from './../../../shared/services/media.service';
import { TranscodeMedia } from './../../../shared/models/transcodemedia.model';
import { Component, OnInit, Input, Output, EventEmitter, OnDestroy } from '@angular/core';
import { SelectionModel } from '@angular/cdk/collections';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';

@Component({
    selector: 'app-transcode-media-table',
    templateUrl: './transcode-media-table.component.html',
    styleUrls: ['./transcode-media-table.component.scss']
})
export class TranscodeMediaTableComponent implements OnInit, OnDestroy {
    displayedColumns: string[] = ['type',
        'filesize', 'container', 'videoCodec', 'audioCodec', 'resolution',
        'bitrate', 'preset', 'complete', 'watch', 'download', 'delete'];

    @Input() flatMediaId: string;
    @Output() noTranscode = new EventEmitter<string>();

    transcodes: TranscodeMedia[] = [];
    innerWidth: number;
    loading = true;
    constructor(private readonly transcodeMediaService: MediaService, private snackBack: MatSnackBar,
        private readonly mediaService: MediaService, public matDialog: MatDialog) { }

    ngOnInit() {
        // TODO: control de observables
        this.innerWidth = window.innerWidth;
        this.transcodeMediaService.getTranscodes(this.flatMediaId).subscribe((transcodes) => {
            this.transcodes = transcodes;
            this.loading = false;
            if (this.transcodes.length === 0) {
                this.openSnackBar('No transcodes for this media', 'OK');
                this.noTranscode.emit('No transcodes for this flatMediaId ' + this.flatMediaId);
            }
        },
            (error) => {
                this.loading = false;
                this.openSnackBar('Error retrieving transcodes of this media', 'OK');
                this.noTranscode.emit('Error retrieving transcodes of this media' + this.flatMediaId);
            });
    }

    openSnackBar(message: string, action?: string) {
        if (action) {
            this.snackBack.open(message, action, { duration: 2000 });
        } else {
            this.snackBack.open(message);
            setTimeout(() => {
                this.snackBack.dismiss();
            }, 2000);
        }
    }
    isMkv(transcodeMedia: TranscodeMedia): boolean {
        return transcodeMedia.container === 'MKV';
    }

    watchVideo(event: UIEvent, flatMediaId: string) {
        event.stopPropagation();
        const url = this.mediaService.watchById(flatMediaId);
        if (url === undefined) {
            // TODO: Sacar stepper de esos que indique que no hay video 
        } else {
            const dialogConfig = new MatDialogConfig();
            let relativeWidth = (this.innerWidth * 30) / 100; // take up to 80% of the screen size
            if (this.innerWidth > 1500) {
                relativeWidth = (1500 * 30) / 100;
            } else {
                relativeWidth = (this.innerWidth * 30) / 100;
            }
            const relativeHeight = (relativeWidth * 9) / 16 + 120; // 16:9 to which we add 120 px for the dialog action buttons ("close")
            dialogConfig.width = relativeWidth + 'px';
            // dialogConfig.height = relativeHeight + 'px';
            dialogConfig.hasBackdrop = false;
            dialogConfig.closeOnNavigation = false;
            dialogConfig.disableClose = false;
            dialogConfig.autoFocus = true;
            dialogConfig.position = {
                'bottom': '30px',
                'left': '30px'
            };
            dialogConfig.data = {
                url: url
            };
            const dialogRef = this.matDialog.open(FloatPlayerComponent, dialogConfig);
            dialogRef.afterClosed().subscribe(result => {
                console.log('The dialog was closed');
            });
        }
    }

    downloadVideo(event: UIEvent, flatMediaId: string) {
        event.stopPropagation();
        this.mediaService.downloadById(flatMediaId);
    }

    deleteMedia(event: UIEvent, transcodeMedia: TranscodeMedia) {
        event.stopPropagation();
        const dialogRef = this.matDialog.open(DialogDeleteMediaComponent, {
            data:
            {
                media: transcodeMedia,
                transcode: true
            }
        });
        dialogRef.afterClosed().subscribe(() => {
            this.transcodeMediaService.getTranscodes(this.flatMediaId);
        });
    }

    ngOnDestroy(): void {
        //Called once, before the instance is destroyed.
        //Add 'implements OnDestroy' to the class.

    }
}
