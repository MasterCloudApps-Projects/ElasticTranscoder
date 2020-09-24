import { MatSnackBar } from '@angular/material/snack-bar';
import { MediaService } from './../../../shared/services/media.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Component, Inject, OnInit } from '@angular/core';

@Component({
  selector: 'app-dialog-delete-media',
  templateUrl: './dialog-delete-media.component.html',
  styleUrls: ['./dialog-delete-media.component.scss']
})
export class DialogDeleteMediaComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<DialogDeleteMediaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private readonly mediaService: MediaService, private snackBar: MatSnackBar) {
  }
  loading = false;
  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  confirmDelete(): void {
    //FIXME: Complete??
    this.loading = true;
    if (this.data.transcode) {
      this.mediaService.deleteTranscode(this.data.media.id).subscribe(
        () => {
          this.openSnackBar('Done!', 'OK');
          this.loading = false;
          this.dialogRef.close();
        },
        (error) => {
          this.openSnackBar('Error deleting transcode, please contact with administrator', 'OK');
          this.dialogRef.close();
        }
      );
    } else {
      this.mediaService.deleteFlat(this.data.media.id).subscribe(
        () => {
          this.openSnackBar('Done!', 'OK');
          this.dialogRef.close();
        },
        (error) => {
          this.openSnackBar('Error deleting media, please contact with administrator', 'OK');
          this.dialogRef.close();
        }
      );
    }
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
}
