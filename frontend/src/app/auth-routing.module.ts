import { LayoutComponent } from './layout/layout.component';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from './shared/interceptors/auth.interceptor';
import { LayoutModule } from '@angular/cdk/layout';

const routes: Routes = [
    { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
    { path: 'dashboard', loadChildren: () => import('./layout/layout.module').then(m => m.LayoutModule) }
];

@NgModule({
    declarations: [],
    imports: [
        CommonModule,
        LayoutModule,
        HttpClientModule,
        RouterModule.forRoot(routes)
    ],
    providers: [],
    exports: [RouterModule]
})
export class AuthRoutingModule { }
