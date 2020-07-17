import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { CashManagerSharedModule } from 'app/shared/shared.module';
import { CashManagerCoreModule } from 'app/core/core.module';
import { CashManagerAppRoutingModule } from './app-routing.module';
import { CashManagerHomeModule } from './home/home.module';
import { CashManagerEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';
import { CashManagerHomeExtendedModule } from 'app/home-extended/home-extended.module';

@NgModule({
  imports: [
    BrowserModule,
    CashManagerSharedModule,
    CashManagerCoreModule,
    CashManagerHomeExtendedModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    CashManagerEntityModule,
    CashManagerAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class CashManagerAppModule {}
