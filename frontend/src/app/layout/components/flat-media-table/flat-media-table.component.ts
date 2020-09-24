import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnDestroy, OnInit, TemplateRef, ViewChild, ViewContainerRef } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { OAuthService } from 'angular-oauth2-oidc';
import { Subscription } from 'rxjs';
import { tap } from 'rxjs/operators';
import { routerTransition } from '../../../router.animations';
import { FlatMediaDatasource } from '../../../shared/datasources/flat-media-datasource';
import { FlatMedia } from './../../../shared/models/flatmedia.model';
import { FloatPlayerComponent } from './../../../shared/modules/float-player/float-player.component';
import { MediaService } from './../../../shared/services/media.service';
import { DialogAddTranscodesComponent } from './../dialog-add-transcodes/dialog-add-transcodes.component';
import { DialogDeleteMediaComponent } from './../dialog-delete-media/dialog-delete-media.component';
import { DialogUploadMediaComponent } from './../dialog-upload-media/dialog-upload-media.component';



@Component({
  selector: 'app-flat-media-table',
  templateUrl: './flat-media-table.component.html',
  styleUrls: ['./flat-media-table.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
    routerTransition()
  ],
})
export class FlatMediaTableComponent implements OnInit, OnDestroy {

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;

  @ViewChild(TemplateRef, { read: ViewContainerRef })

  flatMedia: FlatMedia[] = [];
  dataSource: FlatMediaDatasource;
  isCollapsed = true;
  page = 0;
  pageSize = 5;
  isAuthenticated = false;

  innerWidth: number;

  finished: boolean;
  noVideos: boolean;
  displayedColumns: string[] = ['name', 'type', 'filesize', 'duration', 'container', 'videoCodec', 'audioCodec', 'resolution'
    , 'bitrate', 'watch', 'download', 'edit', 'delete'];
  expandedElement: FlatMedia | null;
  subscriptionAuthenticationState: Subscription;

  constructor(private mediaService: MediaService,
    public matDialog: MatDialog,
    private oauthService: OAuthService
  ) {
  }



  noTranscodeData(message: string) {
    this.expandedElement = null;
  }

  ngOnInit() {
    this.isAuthenticated = this.oauthService.hasValidAccessToken();
    if (this.isAuthenticated) {
      this.loadPaginator();
    }
    this.subscriptionAuthenticationState = this.oauthService.events.subscribe(event => {
      console.log('New Event of oauth');
      console.log(event);
      if ((event.type === 'token_received' || event.type === 'token_refreshed') && this.oauthService.hasValidAccessToken()) {
        this.isAuthenticated = true;
        this.loadPaginator();
      } else {
        this.isAuthenticated = false;
      }
    });
    this.innerWidth = window.innerWidth;
  }

  loadPaginator() {
    this.dataSource = new FlatMediaDatasource(this.mediaService);
    this.dataSource.loadLessons(this.page, this.pageSize);
    if (this.paginator !== undefined) {
      this.paginator.page
        .pipe(
          tap(() => this.loadMedia())
        )
        .subscribe();
    }
  }

  isMkv(flatMedia: FlatMedia): boolean {
    return flatMedia.container === 'MKV';
  }

  loadMedia() {
    this.dataSource.loadLessons(
      this.paginator.pageIndex,
      this.paginator.pageSize);
  }

  uploadContent() {
    const dialogRef = this.matDialog.open(DialogUploadMediaComponent, {
      width: '50%',
      height: '50%',
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.loadMedia();
    });
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

  addTranscodes(event: UIEvent, flatMedia: FlatMedia) {
    event.stopPropagation();

    const dialogConfig = new MatDialogConfig();
    dialogConfig.closeOnNavigation = false;
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      media: flatMedia
    };
    const dialogRef = this.matDialog.open(DialogAddTranscodesComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(() => {
      this.loadMedia();
    });
  }

  deleteMedia(event: UIEvent, flatMedia: FlatMedia) {
    event.stopPropagation();
    const dialogRef = this.matDialog.open(DialogDeleteMediaComponent, {
      data:
      {
        media: flatMedia,
        transcode: false
      }
    });
    dialogRef.afterClosed().subscribe(() => {
      this.loadMedia();
    });
  }

  ngOnDestroy() {
    if (this.subscriptionAuthenticationState) {
      this.subscriptionAuthenticationState.unsubscribe();
    }
  }
}
