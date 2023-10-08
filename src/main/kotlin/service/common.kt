package service

import java.time.Instant
import java.util.UUID

interface Event {
    val id: UUID
    val timestamp: Instant
}
