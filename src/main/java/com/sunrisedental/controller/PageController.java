package com.sunrisedental.controller;

import com.sunrisedental.dto.AppointmentForm;
import com.sunrisedental.exception.BusinessRuleException;
import com.sunrisedental.exception.ResourceNotFoundException;
import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.AppointmentStatus;
import com.sunrisedental.model.Bill;
import com.sunrisedental.repository.DentistRepository;
import com.sunrisedental.repository.TreatmentRepository;
import com.sunrisedental.service.*;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class PageController {

    private final AppointmentService appointmentService;
    private final BillingService billingService;
    private final ReportService reportService;
    private final DentistRepository dentistRepository;
    private final TreatmentRepository treatmentRepository;

    public PageController(
            AppointmentService appointmentService,
            BillingService billingService,
            ReportService reportService,
            DentistRepository dentistRepository,
            TreatmentRepository treatmentRepository) {
        this.appointmentService = appointmentService;
        this.billingService = billingService;
        this.reportService = reportService;
        this.dentistRepository = dentistRepository;
        this.treatmentRepository = treatmentRepository;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("todayCount", appointmentService.countToday());
        model.addAttribute("recentAppointments", appointmentService.recent());
        model.addAttribute("totalRevenue", reportService.totalRevenue());
        return "dashboard";
    }

    @GetMapping("/appointments/new")
    public String newAppointment(Model model) {
        model.addAttribute("appointmentForm", new AppointmentForm());
        loadFormLists(model);
        return "appointment-form";
    }

    @PostMapping("/appointments")
    public String createAppointment(
            @Valid @ModelAttribute("appointmentForm") AppointmentForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            loadFormLists(model);
            return "appointment-form";
        }

        try {
            Appointment created = appointmentService.create(form);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Appointment registered successfully. Number: " + created.getAppointmentNumber());
            return "redirect:/appointments/" + created.getAppointmentNumber();
        } catch (BusinessRuleException | ResourceNotFoundException ex) {
            model.addAttribute("businessError", ex.getMessage());
            loadFormLists(model);
            return "appointment-form";
        }
    }

    @GetMapping("/appointments/search")
    public String searchAppointment(@RequestParam(required = false) String appointmentNumber,
                                    RedirectAttributes redirectAttributes) {
        if (appointmentNumber == null || appointmentNumber.isBlank()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Enter an appointment number.");
            return "redirect:/dashboard";
        }
        return "redirect:/appointments/" + appointmentNumber.trim();
    }

    @GetMapping("/appointments/{appointmentNumber}")
    public String appointmentDetails(@PathVariable String appointmentNumber, Model model) {
        try {
            model.addAttribute("appointment", appointmentService.getByNumber(appointmentNumber));
            model.addAttribute("statuses", AppointmentStatus.values());
            return "appointment-details";
        } catch (ResourceNotFoundException ex) {
            model.addAttribute("message", ex.getMessage());
            return "not-found";
        }
    }

    @PostMapping("/appointments/{appointmentNumber}/status")
    public String updateStatus(@PathVariable String appointmentNumber,
                               @RequestParam AppointmentStatus status,
                               RedirectAttributes redirectAttributes) {
        appointmentService.updateStatus(appointmentNumber, status);
        redirectAttributes.addFlashAttribute("successMessage", "Appointment status updated.");
        return "redirect:/appointments/" + appointmentNumber;
    }

    @PostMapping("/billing/{appointmentNumber}")
    public String generateBill(@PathVariable String appointmentNumber) {
        Bill bill = billingService.generateForAppointment(appointmentNumber);
        return "redirect:/billing/receipt/" + bill.getBillNumber();
    }

    @GetMapping("/billing/receipt/{billNumber}")
    public String receiptByBillNumber(@PathVariable String billNumber, Model model) {
        // Keeping the UI route simple by locating the appointment in the already generated bill list.
        Bill bill = billingService.findByBillNumber(billNumber);
        model.addAttribute("bill", bill);
        return "receipt";
    }

    @GetMapping("/reports")
    public String reports(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {

        LocalDate selectedDate = date == null ? LocalDate.now() : date;
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("appointments", reportService.dailyAppointments(selectedDate));
        model.addAttribute("totalRevenue", reportService.totalRevenue());
        return "reports";
    }

    @GetMapping("/help")
    public String help() {
        return "help";
    }

    private void loadFormLists(Model model) {
        model.addAttribute("dentists", dentistRepository.findByActiveTrueOrderByNameAsc());
        model.addAttribute("treatments", treatmentRepository.findByActiveTrueOrderByNameAsc());
    }
}
