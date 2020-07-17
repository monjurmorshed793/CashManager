import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { AccountService } from '../core/auth/account.service';
import { LoginModalService } from '../core/login/login-modal.service';
import { HomeComponent } from '../home/home.component';

@Component({
  selector: 'jhi-home',
  templateUrl: './home-extended.component.html',
  styleUrls: ['home-extended.scss'],
})
export class HomeExtendedComponent extends HomeComponent implements OnInit, OnDestroy {
  constructor(protected accountService: AccountService, protected loginModalService: LoginModalService) {
    super(accountService, loginModalService);
  }

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }
}
