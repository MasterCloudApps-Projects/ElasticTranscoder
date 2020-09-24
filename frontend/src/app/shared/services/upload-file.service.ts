import { HttpClient, HttpEventType, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { environment } from './../../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  constructor(private httpClient: HttpClient) { }

  public upload(files: Set<File>): { [key: string]: { progress: Observable<number> } } {
    const status: { [key: string]: { progress: Observable<number> } } = {};
    files.forEach(file => {
      const formData: FormData = new FormData();
      formData.append('file', file, file.name);
      const req = new HttpRequest('POST', environment.apiUrl + 'files', formData, {
        reportProgress: true,
      });
      const progress = new Subject<number>();
      this.httpClient.request(req).subscribe(event => {
        if (event.type === HttpEventType.UploadProgress) {
          const percentDone = Math.round(100 * event.loaded / event.total);
          progress.next(percentDone);
        } else if (event instanceof HttpResponse) {
          progress.complete();
        }
      });
      status[file.name] = {
        progress: progress.asObservable()
      };
    });
    return status;
  }

  sendConversion(url: string, formData: FormData): Observable<any> {
    return this.httpClient.post(url, formData, {
      reportProgress: true,
      responseType: 'json'
    });
  }
}
