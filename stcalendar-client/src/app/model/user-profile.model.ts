import { Deserializable } from './deserializable.model';
import { Role } from './role.model';

export class UserProfile implements Deserializable {
  username: string;
  firstName: string;
  lastName: string;
  avatar: string;
  faculty: string;
  clazz: string;
  password: string;
  enabled: boolean;
  ggRefreshToken: string;
  createdAt: string;
  updatedAt: string;
  role: Role;

  deserialize(input: any): this {
    Object.assign(this, input);
    this.role = new Role().deserialize(input.role);
    return this;
  }
}
