import avatar1 from '../assets/avatar/avatar1.png';
import avatar2 from '../assets/avatar/avatar2.png';
import avatar3 from '../assets/avatar/avatar3.png';
import avatar4 from '../assets/avatar/avatar4.png';
import avatar5 from '../assets/avatar/avatar5.png';
import avatar6 from '../assets/avatar/avatar6.png';

const avatars = [
    avatar1,
    avatar2,
    avatar3,
    avatar4,
    avatar5,
    avatar6,
] as const;

export function getAvatarUrl(index?: number): string {
    if (typeof index !== 'number' || index < 0 || index >= avatars.length) {
        return avatars[0];
    }
    return avatars[index];
}