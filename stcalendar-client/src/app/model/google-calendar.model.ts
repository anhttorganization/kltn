export class GoogleCalendar {
  id: string;
  summary: string;
  timeZone: string;

  deserializable(input: any) {
    Object.assign(this, input);
    return this;
  }
}
