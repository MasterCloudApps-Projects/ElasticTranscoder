import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDividerModule } from '@angular/material/divider';
import { MatTableModule } from '@angular/material/table';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { FloatPlayerComponent } from './float-player.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import {VgCoreModule} from '@videogular/ngx-videogular/core';
import {VgControlsModule} from '@videogular/ngx-videogular/controls';
import {VgBufferingModule} from '@videogular/ngx-videogular/buffering';
import {VgStreamingModule} from '@videogular/ngx-videogular/streaming';
import {VgOverlayPlayModule} from '@videogular/ngx-videogular/overlay-play';


@NgModule({
    imports: [
        CommonModule,
        MatTableModule,
        MatDividerModule,
        MatCheckboxModule,
        MatIconModule,
        MatFormFieldModule,
        MatButtonModule,
        DragDropModule,
        MatDialogModule,
        MatIconModule,
        VgCoreModule,
        VgControlsModule,
        VgBufferingModule,
        VgStreamingModule,
        VgOverlayPlayModule
    ],
    declarations: [FloatPlayerComponent],
    exports: [FloatPlayerComponent]
})
export class FloatPlayerModule { }
