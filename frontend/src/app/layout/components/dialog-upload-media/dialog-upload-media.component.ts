import { MatSnackBar } from '@angular/material/snack-bar';
import { UploadFileService } from './../../../shared/services/upload-file.service';
import { Component, OnInit, ViewChild } from '@angular/core'
import { MatDialogRef } from '@angular/material/dialog'
import { forkJoin } from 'rxjs'

@Component({
  selector: 'app-dialog-upload-media',
  templateUrl: './dialog-upload-media.component.html',
  styleUrls: ['./dialog-upload-media.component.scss']
})
export class DialogUploadMediaComponent implements OnInit {


  constructor(public dialogRef: MatDialogRef<DialogUploadMediaComponent>,
    public uploadService: UploadFileService, private snackBack: MatSnackBar) { }

  @ViewChild('file') file;
  public files: Set<File> = new Set();
  progress;
  canBeClosed = true;
  primaryButtonText = 'Upload';
  showCancelButton = true;
  showFinishButton = false;

  uploading = false;
  uploadSuccessful = false;

  ngOnInit() {

  }
  addFiles() {
    this.file.nativeElement.click();
  }

  onFilesAdded() {
    const files: { [key: string]: File } = this.file.nativeElement.files;
    for (let key in files) {
      if (!isNaN(parseInt(key))) {
        this.files.add(files[key]);
      }
    }
  }

  deleteFile(file: File) {
    this.files.delete(file);
  }

  closeDialog() {
    // if everything was uploaded already, just close the dialog
    if (this.uploadSuccessful) {
      return this.dialogRef.close();
    }
    // set the component state to "uploading"
    this.uploading = true;
    // start the upload and save the progress map
    this.progress = this.uploadService.upload(this.files);
    // convert the progress map into an array
    let allProgressObservables = [];
    for (let key in this.progress) {
      allProgressObservables.push(this.progress[key].progress);
    }
    // Adjust the state variables
    // The OK-button should have the text "Finish" now
    this.primaryButtonText = 'Finish';
    // The dialog should not be closed while uploading
    this.canBeClosed = false;
    this.dialogRef.disableClose = true;
    // Hide the cancel-button
    this.showCancelButton = false;
    // When all progress-observables are completed...
    forkJoin(allProgressObservables).subscribe((end) => {
      // ... the dialog can be closed again...
      this.canBeClosed = true;
      this.dialogRef.disableClose = false;
      // ... the upload was successful...
      this.uploadSuccessful = true;
      // ... and the component is no longer uploading
      this.uploading = false;
      this.showFinishButton = true;
    }, (error) => {
      this.openSnackBar('Error uploading files');
      this.canBeClosed = true;
      this.dialogRef.disableClose = false;
      this.uploadSuccessful = true;
      this.uploading = false;
      this.showFinishButton = true;
    });
  }
  openSnackBar(message: string) {
    this.snackBack.open(message, 'Ok', {
      duration: 5000,
    });
  }
}
