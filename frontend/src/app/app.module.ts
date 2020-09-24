import { DragDropModule } from '@angular/cdk/drag-drop';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { OAuthModule } from 'angular-oauth2-oidc';
import { AppComponent } from './app.component';
import { AuthRoutingModule } from './auth-routing.module';
import { AuthInterceptor } from './shared/interceptors/auth.interceptor';
import { MediaService } from './shared/services/media.service';
import { UploadFileService } from './shared/services/upload-file.service';
import { WebSocketService } from './shared/services/websocket.service';


@NgModule({
    imports: [
        CommonModule,
        BrowserModule.withServerTransition({ appId: 'serverApp' }),
        BrowserModule.withServerTransition({ appId: 'my-app' }),
        BrowserAnimationsModule,
        HttpClientModule,
        AuthRoutingModule,
        ReactiveFormsModule,
        DragDropModule,
        FormsModule,
        OAuthModule.forRoot()
    ],
    declarations: [AppComponent],
    providers: [
        MediaService,
        UploadFileService,
        WebSocketService,
        { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
 
    ],
    exports: [DragDropModule],
    bootstrap: [AppComponent]
})
export class AppModule { }
