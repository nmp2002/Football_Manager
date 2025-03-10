import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentResultComponent  } from './payment-result.component';

describe(' SearchFieldResultComponent ', () => {
  let component:  PaymentResultComponent  ;
  let fixture: ComponentFixture< PaymentResultComponent  >;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [  PaymentResultComponent  ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent( PaymentResultComponent  );
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
