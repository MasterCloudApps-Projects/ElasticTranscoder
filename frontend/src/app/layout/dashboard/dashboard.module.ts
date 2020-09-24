import { FloatPlayerModule } from './../../shared/modules/float-player/float-player.module';
import { MatDialogModule } from '@angular/material/dialog';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTableModule } from '@angular/material/table';
import { LayoutModule } from './../layout.module';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard.component';

@NgModule({
    imports: [
        CommonModule,
        DashboardRoutingModule,
        MatButtonModule,
        MatDividerModule,
        LayoutModule,
        MatTableModule,
        MatDividerModule,
        MatCheckboxModule,
        MatIconModule,
        MatButtonModule,
        InfiniteScrollModule,
        FloatPlayerModule,
        MatDialogModule,
    ],
    declarations: [
        DashboardComponent,

    ]
})
export class DashboardModule { }
