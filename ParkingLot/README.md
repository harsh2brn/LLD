## Step 1: Requirements
### Functional requirements:
1. Entry Flow:
    - Vehicle arrives at gate
    - Assign a slot
    - Generate Ticket
    - Mark slot as occupied
    - Return entry response

2. Exit Flow:
    - Vehicle presents the ticket
    - Calculate the price according to the rules
    - Process Payment
    - Release slot
    - Return exit response

3. Admin Flow:
    - CRUD floors and tickets
    - Define/Update pricing rules
    - View Parking lot status

### Edge Cases:
1. Payment Failure at exit
2. Fake Ticket
3. Slot marked occupied incorrectly


## Step 2: Identify Core Entities
| Entity                | Attributes                    
| -                     | -                             
| Vehicle               | id, licensePlate, vehicleType
| Ticket                | id, vehicleId, slotId, entryTime, isActive
| Slot           | id, slotType, vehicleId, isOccupied, floorNumber
| Receipt               | id, amount, ticketId, exitTime, paymentStatus
| Floor                 | id, floorNumber, slots
| PricingRule           | vehicleType, ratePerHour, flatRate, ruleType
| Entry/Exit Result     | success, data, message
| Payment               | ticketId, status, gateway, amount


## Step 3: Visual Flows

1. Entry Flow Diagram

```mermaid
flowchart LR
    Vehicle[Vehicle Arrives] --> SlotCheck{Is slot available?}
    SlotCheck --> |Yes| SlotAllocated[Slot allocated]
    SlotCheck --> |No| NoSlotAvailableResponse[No Slot Available]
    SlotAllocated --> Ticket[Ticket Generated]
    Ticket --> SlotMarked[Slot Marked as Occupied]
```
---
<br>

2. Exit Flow Diagram

```mermaid
flowchart LR
    TicketScanned[Ticket Scanned] --> FeeCalculated[Fee Calculated]
    FeeCalculated[Fee Calculated] --> Payment{Payment?}
    Payment{Payment?} --> |Successful| Receipt[Receipt Generated]
    Payment{Payment?} --> |Not Successful| Retry[Retry]
    Receipt[Receipt Generated] --> SlotRelease[Slot Released]
    Ticket[Ticket Deactivated]
```
---
<br>

3. Admin Flow

```mermaid
flowchart TD
    Admin[Admin] --> AddFloor[CRUD Floor]
    Admin[Admin] --> AddSlot[CRUD Slot]
    Admin[Admin] --> Pricing[Update pricing rules]
```
---
<br>


## Step 4: Define Class Structure & Relationships
```mermaid
flowchart LR
    Client(Client) --> Controller(Controller) --> Service(Service) --> Repository(Repository) --> Domain(Domain)
```
---

### Controllers
- <b>ParkingLotController:</b> Endpoints for EnterVehicle, ExitVehicle
- <b>AdminController:</b> Endpoints for AddFloor, AddSlot, UpdatePricing

---

### Services
- <b>TicketService:</b> Generates, validates, deactivates, retrieves tickets
- <b>SlotService:</b> Allocates and releases parking slots
- <b>PricingService:</b> Calculates parking fee based on duration and type
- <b>PaymentService:</b> Processes payment
- <b>ReceiptService:</b> Generates receipt after payment
- <b>AdminService:</b> Handles admin related tasks

---

### Repositories
<b>TicketRepository, SlotRepository, FloorRepository, PaymentRepository, PricingRepository</b>
<br>
All these allow CRUD operations separated from service layer.

---

## Step 5: Implement Core Use Cases

### Entry Use Case: 
entryEndpoint -> SlotService.allocateSlot(), TicketService.generateTicket(), TicketRepository.save(), Return EntryResult

### Exit Use Case:
exitEndpoint -> TicketService.retrieveAndDeactivateTicket(), PricingService.calculateFee(), PaymentService.processPayment(), ReceiptService.generateReceipt(), SlotService.releaseSlot(), Return ExitResult

---

## Step 6: Apply OOP Principles & Design Patterns

### Design Patterns Used:
- <b> Adapter Pattern </b> Abstraction of payment gateways
- <b> Repository Pattern </b> Isolation of database operations
- <b> Service Layer Pattern </b> Centralization of business logic
- <b> Factory Method Pattern </b> can be used for the creation of vehicles
- <b> Strategy Pattern </b> can be used to select the pricing strategy

### OOP Principles Applied:
- <b> SRP (Single Responsibility Principle) </b> Each class has one clear responsibility
- <b> ISP (Interface Seggregation Principle) </b> Role-specific interfaces (e.g. for payment, vehicle)
- <b> DIP (Dependency Inversion Principle) </b> Services depend on interface, not concrete implementations
- <b> OCP (Open/Closed Principle) </b> System is open for extension but closed for modification
- <b> Encapsulation </b> Entities, services and repositories encapsulate both data and behaviour

---
<br><br><br><br><br><br><br><br>
