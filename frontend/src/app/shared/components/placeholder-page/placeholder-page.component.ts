import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-placeholder-page',
  template: `
    <div class="placeholder">
      <h1>{{ title }}</h1>
      <p>Cette section sera disponible prochainement.</p>
    </div>
  `,
  styles: `
    .placeholder {
      padding: 32px 40px;
    }
    h1 {
      margin: 0 0 12px;
      font-size: 24px;
      font-weight: 700;
      color: var(--color-text);
    }
    p {
      color: var(--color-text-muted);
      font-size: 14px;
    }
  `,
})
export class PlaceholderPageComponent implements OnInit {
  title = 'Page';

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.title = this.route.snapshot.data['title'] ?? 'Page';
  }
}
