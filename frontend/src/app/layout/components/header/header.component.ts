import { OAuthService, UserInfo, OAuthSuccessEvent } from 'angular-oauth2-oidc';
import { Subscription } from 'rxjs';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { User } from '../../../shared/models/user.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';



@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy {

    isAuthenticated = false;
    user: string;

    subscriptionAuthenticationState: Subscription;
    constructor(public router: Router,
        private oauthService: OAuthService
    ) {
    }

    login() {
        this.oauthService.initLoginFlow();
    }

    logout() {
        this.oauthService.logOut();
    }

    ngOnInit() {
        this.isAuthenticated = this.oauthService.hasValidAccessToken();
        if (this.isAuthenticated) {
            this.user = this.oauthService.getIdentityClaims()['name'];
        }
        this.subscriptionAuthenticationState = this.oauthService.events.subscribe(event => {
            console.log('New Event of oauth');
            console.log(event);
            if ((event.type === 'token_received' || event.type === 'token_refreshed') && this.oauthService.hasValidAccessToken()) {
                this.user = this.oauthService.getIdentityClaims()['name'];
                this.isAuthenticated = true;
            } else {
                this.user = undefined;
                this.isAuthenticated = false;
            }
        });
    }

    ngOnDestroy(): void {
        if (this.subscriptionAuthenticationState) {
            this.subscriptionAuthenticationState.unsubscribe();
        }
    }
}
