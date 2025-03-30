package com.ajesh.hellotaxi.controller;

import com.ajesh.hellotaxi.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller for handling analytics-related operations.
 */
@Controller
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    /**
     * Displays analytics data related to bookings.
     *
     * @param model The model to store analytics data for the view.
     * @return The name of the analytics view template.
     */
    @GetMapping
    public String showAnalytics(Model model) {
        Map<String, Object> analyticsData = analyticsService.getBookingAnalytics();

        model.addAttribute("totalBookings", analyticsData.get("totalBookings"));
        model.addAttribute("completedBookings", analyticsData.get("completedBookings"));
        model.addAttribute("canceledBookings", analyticsData.get("canceledBookings"));
        model.addAttribute("bookingsPerDay", analyticsData.get("bookingsPerDay"));
        model.addAttribute("mostActiveTaxis", analyticsData.get("mostActiveTaxis"));

        return "analytics";
    }
}
