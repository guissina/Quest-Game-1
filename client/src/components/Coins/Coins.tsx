import styles from './Coins.module.scss'
import { Badge } from 'lucide-react'

export default function Coins({value}: {value: number}) {
  return (
    <div className={styles.coin}>
        <Badge size={32} className={styles.badge} />
        <span>{value}</span>
    </div>
  )
}
