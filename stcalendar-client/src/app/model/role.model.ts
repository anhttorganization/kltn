import { Deserializable } from './deserializable.model';
export class Role implements Deserializable{
    id: number;
    name: string;

    deserialize(input: any): this {
        Object.assign(this, input);
        return this;
      }
}
