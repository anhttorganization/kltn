import { Deserializable } from './../../../../model/deserializable.model';
export class WorkingEventVo  implements Deserializable{

  day: string;
  date: string;
  start: string;
  end: string;
  local: string;
  content: string;
  component: string;
  leadder: string;

  deserialize(input: any): this {
    Object.assign(this, input);
    return this;
  }
}
