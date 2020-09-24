import { OAuthService, JwksValidationHandler, AuthConfig } from 'angular-oauth2-oidc';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';

export const authConfig: AuthConfig = {
    issuer: 'https://login.videotranscoding.es/oauth2/default',
    redirectUri: window.location.origin,
    clientId: '0oabsfibn6o1fRU704x6',
    scope: 'openid profile',
    showDebugInformation: false,
    responseType: 'code',
};

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AppComponent {
    constructor(private oauthService: OAuthService) {
        this.oauthService.configure(authConfig);
        this.oauthService.loadDiscoveryDocumentAndTryLogin();

    }
}
