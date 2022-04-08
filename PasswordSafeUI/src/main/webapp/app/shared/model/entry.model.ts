import { IGroup } from '@/shared/model/group.model';

export interface IEntry {
  id?: number;
  description?: string | null;
  login?: string;
  passwort?: string;
  passwortReplay?: string;
  group?: IGroup | null;
}

export class Entry implements IEntry {
  constructor(
    public id?: number,
    public description?: string | null,
    public login?: string,
    public passwort?: string,
    public passwortReplay?: string,
    public group?: IGroup | null
  ) {}
}
