import { MatSnackBar } from '@angular/material/snack-bar';
import { FlatMedia } from './../../../shared/models/flatmedia.model';
import { Transcode } from './../../../shared/models/transcode-selected.model';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranscodeApi } from './../../../shared/models/transcode.model';
import { MediaService } from './../../../shared/services/media.service';
import { Component, OnInit, Input, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { take, finalize } from 'rxjs/operators';
import { transcode } from 'buffer';
@Component({
  selector: 'app-dialog-add-transcodes',
  templateUrl: './dialog-add-transcodes.component.html',
  styleUrls: ['./dialog-add-transcodes.component.scss']
})
export class DialogAddTranscodesComponent implements OnInit {

  transcodes: TranscodeApi;
  loading = true;
  transcodesFormGroup: FormGroup;

  constructor(public dialogRef: MatDialogRef<DialogAddTranscodesComponent>,
    private formBuilder: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private readonly mediaService: MediaService,
    private snackBar: MatSnackBar) {

  }

  ngOnInit(): void {
    this.mediaService.getTranscodesByType('video').pipe(take(1)).subscribe(
      (transcodes) => {
        this.transcodes = transcodes;
        this.prepareFormControls();
        this.loading = false;
      }
    );
  }
  private prepareFormControls() {
    if (this.data.media.type === 'video') {
      this.transcodesFormGroup = this.formBuilder.group({
        containersVideo: [null, Validators.required],
        codecsVideo: [null, Validators.required],
        codecsAudio: [null, Validators.required],
        presets: [null, Validators.required],
        resolutions: [null, Validators.required]
      });
    } else {
      this.transcodesFormGroup = this.formBuilder.group({
        containersAudio: [null, Validators.required],
        codecsAudio: [null, Validators.required]
      });
    }
  }

  cancel() {
    this.dialogRef.close();
  }

  onSubmitVideo() {
    this.loading = true;
    this.mediaService.createTranscodes(this.data.media.id, this.createTranscodesVideo()).subscribe(
      (next) => {
        this.dialogRef.close();
        this.openSnackBar('Done!', 'OK');
      },
      (error) => {
        this.openSnackBar('Error sending transcodes, please contact with administrator', 'OK');
      },
      () => {
        this.loading = false;
      }
    );
  }
  onSubmitAudio() {
    this.loading = true;
    this.mediaService.createTranscodes(this.data.media.id, this.createTranscodesAudio()).subscribe(
      (next) => {
        this.dialogRef.close();
        this.openSnackBar('Done!', 'OK');

      },
      (error) => {
        this.openSnackBar('Error sending transcodes, please contact with administrator', 'OK');
      },
      () => {
        this.loading = false;
      }
    );
  }
  private openSnackBar(message: string, action?: string) {
    if (action) {
      this.snackBar.open(message, action, { duration: 2000 });
    } else {
      this.snackBar.open(message);
      setTimeout(() => {
        this.snackBar.dismiss();
      }, 2000);
    }
  }

  private createTranscodesVideo(): Transcode[] {
    const transcodes: Transcode[] = [];
    // FIXME: very ugly
    for (let i = 0; i < this.transcodesFormGroup.value.containersVideo.length; i++) {
      for (let j = 0; j < this.transcodesFormGroup.value.codecsVideo.length; j++) {
        for (let k = 0; k < this.transcodesFormGroup.value.resolutions.length; k++) {
          for (let l = 0; l < this.transcodesFormGroup.value.codecsAudio.length; l++) {
            for (let m = 0; m < this.transcodesFormGroup.value.presets.length; m++) {
              transcodes.push({
                resolution: this.transcodesFormGroup.value.resolutions[k],
                videoCodec: this.transcodesFormGroup.value.codecsVideo[j],
                videoContainer: this.transcodesFormGroup.value.containersVideo[i],
                audioCodec: this.transcodesFormGroup.value.codecsAudio[l],
                preset: this.transcodesFormGroup.value.presets[m],
                audioContainer: null
              });
            }
          }
        }
      }
    }
    return transcodes;
  }
  private createTranscodesAudio(): Transcode[] {
    const transcodes: Transcode[] = [];
    // FIXME: very ugly
    for (let i = 0; i < this.transcodesFormGroup.value.containersAudio.length; i++) {
      for (let l = 0; l < this.transcodesFormGroup.value.codecsAudio.length; l++) {
        transcodes.push({
          resolution: null,
          videoCodec: null,
          videoContainer: null,
          audioCodec: this.transcodesFormGroup.value.codecsAudio[l],
          preset: null,
          audioContainer: this.transcodesFormGroup.value.containersAudio[i]
        });
      }
    }
    return transcodes;
  }
}
