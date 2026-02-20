export interface PostModel {
  id: number;
  Content: string;
  mediaPath?: string;
  CreatedAt?: Date;
  authorId: number;
}

