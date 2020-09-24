import { MatListModule } from '@angular/material/list';
import { FloatPlayerModule } from './../shared/modules/float-player/float-player.module';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { TranscodeMediaTableComponent } from './components/transcode-media-table/transcode-media-table.component';
import { TranscoderStatusComponent } from './components/transcoder-status/transcoder-status.component';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDividerModule } from '@angular/material/divider';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LayoutRoutingModule } from './layout-routing.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { LayoutComponent } from './layout.component';
import { HeaderComponent } from './components/header/header.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { FlatMediaTableComponent } from './components/flat-media-table/flat-media-table.component';
import { DialogAddTranscodesComponent } from './components/dialog-add-transcodes/dialog-add-transcodes.component';
import { DialogDeleteMediaComponent } from './components/dialog-delete-media/dialog-delete-media.component';
import { DialogUploadMediaComponent } from './components/dialog-upload-media/dialog-upload-media.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatStepperModule } from '@angular/material/stepper';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatMenuModule } from '@angular/material/menu';

@NgModule({
    imports: [
        CommonModule,
        LayoutRoutingModule,
        ReactiveFormsModule,
        FormsModule,
        MatToolbarModule,
        MatIconModule,
        MatButtonModule,
        MatTableModule,
        MatDividerModule,
        MatTableModule,
        MatDividerModule,
        MatCheckboxModule,
        MatIconModule,
        InfiniteScrollModule,
        FloatPlayerModule,
        MatDialogModule,
        FlexLayoutModule,
        MatTableModule,
        MatDividerModule,
        MatCheckboxModule,
        MatIconModule,
        MatProgressBarModule,
        MatDividerModule,
        MatCheckboxModule,
        MatSnackBarModule,
        MatListModule,
        MatPaginatorModule,
        MatProgressSpinnerModule,
        MatInputModule,
        MatFormFieldModule,
        MatSelectModule,
        MatTooltipModule,
        MatMenuModule,
    ],
    declarations: [
        LayoutComponent,
        HeaderComponent,
        FlatMediaTableComponent,
        DialogAddTranscodesComponent,
        DialogDeleteMediaComponent,
        DialogUploadMediaComponent,
        TranscoderStatusComponent,
        TranscodeMediaTableComponent,

    ],
    exports: [
        FlatMediaTableComponent,
        DialogAddTranscodesComponent,
        DialogDeleteMediaComponent,
        DialogUploadMediaComponent,
        TranscoderStatusComponent
    ]
})
export class LayoutModule { }
