# Maintenance Request System – Backend

**Student:** Igiraneza Irene  
**Student ID:** 27328  
**Course:** INSY 8322 – Web Technology and Internet  
**Project:** Maintenance Request System  

---

## Prerequisites

- Java 21
- PostgreSQL running locally
- Maven (or use the included `mvnw` wrapper)

---

## Database Setup

Create a PostgreSQL database named `maintenance_request_db`:

```sql
CREATE DATABASE maintenance_request_db;
```

Default credentials used in `application.properties`:
- **username:** postgres  
- **password:** postgres  
- **port:** 5432  

Update `src/main/resources/application.properties` if your credentials differ.

---

## How to Run

```bash
# Clone the repository
git clone https://github.com/YOUR_USERNAME/maintenance-request-system-backend.git
cd maintenance-request-system-backend

# Run the application
./mvnw spring-boot:run      # Linux/Mac
mvnw.cmd spring-boot:run    # Windows
```

The API will start on **http://localhost:8080**.

---

## API Endpoints Summary

### Trades – `/api/trades`
| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/trades/save` | Save a trade |
| GET | `/api/trades/all` | Get all trades |
| GET | `/api/trades/{id}` | Get trade by id |
| PUT | `/api/trades/update/{id}` | Update a trade |
| DELETE | `/api/trades/delete/{id}` | Delete a trade |
| GET | `/api/trades/unused` | Trades no technician holds (C3) |

### Buildings – `/api/buildings`
| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/buildings/save` | Save a building |
| GET | `/api/buildings/all` | Get all buildings |
| GET | `/api/buildings/{id}` | Get building by id |
| PUT | `/api/buildings/update/{id}` | Update a building |
| DELETE | `/api/buildings/delete/{id}` | Delete a building |
| GET | `/api/buildings/free` | Buildings with no technician (C2) |

### Technicians – `/api/technicians`
| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/technicians/save` | Save a technician |
| GET | `/api/technicians/all` | Get all technicians |
| GET | `/api/technicians/{id}` | Get technician by id |
| PUT | `/api/technicians/update/{id}` | Update a technician |
| DELETE | `/api/technicians/delete/{id}` | Delete a technician |
| GET | `/api/technicians/by-trade?name=plumb` | Technicians by trade name (C1) |
| PUT | `/api/technicians/{technicianId}/trades/{tradeId}` | Add trade to technician (D3) |
| PATCH | `/api/technicians/{technicianId}/building?buildingNumber=4` | Move to building (D4) |

### Requesters – `/api/requesters`
| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/requesters/save` | Save a requester |
| GET | `/api/requesters/all` | Get all requesters |
| GET | `/api/requesters/{id}` | Get requester by id |
| PUT | `/api/requesters/update/{id}` | Update a requester |
| DELETE | `/api/requesters/delete/{id}` | Delete a requester |
| GET | `/api/requesters/by-lastname?lastName=igiraneza` | By last name (B1) |
| GET | `/api/requesters/of-technician/{technicianId}` | Requesters of a technician (C4) |

### Maintenance Requests – `/api/maintenance-requests`
| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/maintenance-requests/save` | Save with business rules (A2+D1) |
| GET | `/api/maintenance-requests/all` | Get all |
| GET | `/api/maintenance-requests/{id}` | Get by id |
| PUT | `/api/maintenance-requests/update/{id}` | Update (no status change) |
| DELETE | `/api/maintenance-requests/delete/{id}` | Delete (protected if repair records exist) |
| GET | `/api/maintenance-requests/by-status?status=SUBMITTED` | By status (B2) |
| GET | `/api/maintenance-requests/between?start=2026-10-01&end=2026-10-31` | By date range (B3) |
| PATCH | `/api/maintenance-requests/{id}/status?status=ASSIGNED` | Status flow (D2) |
| GET | `/api/maintenance-requests/stats/by-status` | Count per status (F1) |
| PATCH | `/api/maintenance-requests/cancel-day?technicianId=...&date=2026-10-20` | Cancel day (F2) |
| GET | `/api/maintenance-requests/page?page=0&size=5&sort=requestDate,desc` | Pagination (Bonus) |

### Repair Records – `/api/repair-records`
| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/repair-records/save?maintenanceRequestId=...` | Save repair record (E1) |
| GET | `/api/repair-records/all` | Get all |
| GET | `/api/repair-records/{id}` | Get by id |
| PUT | `/api/repair-records/update/{id}` | Update |
| DELETE | `/api/repair-records/delete/{id}` | Delete (E3) |
| GET | `/api/repair-records/requester/{staffNumber}` | History by requester (E2) |

### Dashboard – `/api/dashboard`
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/dashboard/summary` | System summary counts (Bonus) |
