export class WorkingEventVo {
  day: string;
  date: string;
  start: string;
  end: string;
  local: string;
  content: string;
  component: string;
  leadder: string;

  deserializable(input: any) {
    Object.assign(this, input);
    this.start = input.start;
    this.end = input.end;

    return this;
  }
}
