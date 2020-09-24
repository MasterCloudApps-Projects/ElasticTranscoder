import { MediaService } from '../services/media.service';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { FlatMedia } from '../models/flatmedia.model';
import { CollectionViewer, DataSource, SelectionModel } from '@angular/cdk/collections';
import { catchError, finalize } from 'rxjs/operators';


export class FlatMediaDatasource implements DataSource<FlatMedia> {

    private lessonsSubject = new BehaviorSubject<FlatMedia[]>([]);

    private loadingSubject = new BehaviorSubject<boolean>(false);
    public loading$ = this.loadingSubject.asObservable();

    private totalElementsSubject = new BehaviorSubject<number>(0);
    public totalElements$ = this.totalElementsSubject.asObservable();



    constructor(private mediaService: MediaService) { }

    connect(collectionViewer: CollectionViewer): Observable<FlatMedia[]> {
        return this.lessonsSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.lessonsSubject.complete();
        this.loadingSubject.complete();
    }

    loadLessons(pageIndex: number, pageSize: number) {
        this.loadingSubject.next(true);
        this.mediaService.getAllMediaByPageable(pageIndex, pageSize).pipe(
            catchError(() => of([])),
            finalize(() => this.loadingSubject.next(false))
        ).subscribe(lessons => {
            this.lessonsSubject.next(lessons.content);
            // this.activeFlatMedia = lessons.content;
            this.totalElementsSubject.next(lessons.totalElements);
        }
        );
    }



    // /** Whether the number of selected elements matches the total number of rows. */
    // isAllSelected() {
    //     const numSelected = this.selection.selected.length;
    //     const numRows = this.activeFlatMedia.length;
    //     return numSelected === numRows;
    // }

    // /** Selects all rows if they are not all selected; otherwise clear selection. */
    // masterToggle() {
    //     this.isAllSelected() ?
    //         this.selection.clear() :
    //         this.activeFlatMedia.forEach(row => this.selection.select(row));
    // }
}