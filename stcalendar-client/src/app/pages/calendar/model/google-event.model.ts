import {Deserializable} from '../../../model/deserializable.model';


export class GoogleEvent {
  id: string;
  sumary: string;
  start: any;
  end: any;
  sequence: number;
  description: string;
  location: string;

  deserializable(input: any) {
    Object.assign(this, input);
    this.start = input.start;
    this.end = input.end;

    return this;
  }

}
