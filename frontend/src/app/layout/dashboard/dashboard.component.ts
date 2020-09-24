import { Component, OnDestroy, OnInit } from '@angular/core';
import { routerTransition } from '../../router.animations';
import { MediaService } from '../../shared/services/media.service';
import { Observable, Subscription } from 'rxjs';
import { interval } from 'rxjs';
import { Router } from '@angular/router';

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss'],
    animations: [routerTransition()]
})
export class DashboardComponent implements OnInit, OnDestroy {
    // originalVideos: Array<Original> = [];
    // originalOnProgress: Original = { id: 0, name: "", path: "", userVideo: null, fileSize: "", conversions: null, complete: false, active: false };
    // conversionsConverted: Array<Conversion> = [];
    // conversionOnProgress: Conversion = { active: false, fileSize: "", finished: false, name: "", id: 0, conversionType: null, parent: null, path: "", progress: "" };
    onConversion: boolean = false;
    loading: boolean = false;

    interval: Subscription = undefined;
    constructor(private router: Router, private mediaService: MediaService) {
    }

    ngOnInit() {
        // //this.ng4LoadingSpinnerService..show();
        this.loading = false;
        // this.getAllMedia();
    }

    // getAllMedia(): any {
    //     this.mediaService.getAllMediaByPageableForDashboard(0).subscribe(
    //         result => {
    //             this.originalVideos = result;
    //             this.getOnProgress()
    //         },
    //         error => {
    //             if (error.status == 0) {
    //                 // this.userService.deleteUserLogged();
    //                 // this.router.navigate(['/login']);
    //                 //this.ng4LoadingSpinnerService..hide();
    //                 return null;
    //             } else if (error.status == 404) {
    //                 this.loading = false;
    //                 //this.ng4LoadingSpinnerService..hide();
    //             }
    //             if (this.interval == undefined) {
    //                 this.interval = interval(60000).subscribe(x => {
    //                     this.ngOnInit();
    //                 });
    //             }
    //         })
    // }
    // getOnProgress(): any {
    //     for (let element of this.originalVideos) {
    //         console.log("Comparando objeto: " + element.name + " y su valor es: " + element.active)
    //         if (element.active == true) {
    //             // console.log("Conversion Activa: " + element.id)
    //             this.originalOnProgress = element;
    //             this.onConversion = true;
    //             this.updateOnConversion();
    //             return null;
    //         }
    //     }
    //     if (this.originalOnProgress.id == 0) {
    //         //this.ng4LoadingSpinnerService..hide();
    //         this.loading = false;
    //     }
    //     if (this.interval == undefined) {
    //         this.interval = interval(60000).subscribe(x => {
    //             this.ngOnInit();
    //         });
    //     }
    // }
    // updateOnConversion() {
    //     if (this.originalOnProgress.active) {
    //         this.mediaService.getOriginalById(this.originalOnProgress.id).subscribe(
    //             result => {
    //                 this.onConversion = true;
    //                 this.originalOnProgress = result;
    //                 this.conversionsConverted = [];
    //                 this.originalOnProgress.conversions.forEach(
    //                     conversion => {
    //                         if (conversion.active == true) {
    //                             //this.ng4LoadingSpinnerService..hide();
    //                             this.loading = false;
    //                             this.conversionOnProgress = conversion;
    //                         }
    //                         if (conversion.finished == true) {
    //                             this.conversionsConverted.push(conversion);
    //                         }
    //                     })

    //                 if (this.interval == undefined) {
    //                     console.log("Definiiendo el intervalor cada 2 segundos")
    //                     this.interval = interval(2000).subscribe(x => {
    //                         this.updateOnConversion();
    //                     });
    //                 }
    //             },
    //             error => console.log(error)
    //         );
    //     }
    //     if (this.originalOnProgress.complete == true) {
    //         this.interval = undefined;
    //         this.onConversion = false;
    //         this.loading = true;
    //         this.originalOnProgress = { id: 0, name: "", path: "", userVideo: null, fileSize: "", conversions: null, complete: false, active: false };
    //         this.ngOnInit();
    //     }

    // }

    ngOnDestroy() {
        if (this.interval != undefined) {
            this.interval.unsubscribe();
        }
    }
    // getErrored(conversion: Conversion): boolean {
    //     return this.mediaService.getErroredOnConversion(conversion);
    // }
    watchVideo(idRedirect: number, idWatchRedirect: number) {
        this.router.navigate(['/watch-video/' + idRedirect], { queryParams: { idWatch: idWatchRedirect } });
    }
    // downloadVideo(idDownload: number) {
    //     this.mediaService.downloadById(idDownload);
    // }

}
