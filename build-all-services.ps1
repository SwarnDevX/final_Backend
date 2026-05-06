# Build All Services Script
# Builds all microservices in the correct order

Write-Host "==================================================" -ForegroundColor Cyan
Write-Host "  BuildLedger - Building All Microservices" -ForegroundColor Cyan
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host ""

$baseDir = "C:\Users\2479792\Videos\MicroserviceBuildLedger"

$services = @(
    "eureka-server",
    "api-gateway",
    "iam-service",
    "vendor-service",
    "contract-service",
    "delivery-service",
    "finance-service",
    "compliance-service",
    "notification-service",
    "report-service"
)

$successCount = 0
$failCount = 0
$failedServices = @()

foreach ($service in $services) {
    Write-Host "[$($services.IndexOf($service) + 1)/$($services.Count)] Building $service..." -ForegroundColor Cyan

    Push-Location "$baseDir\$service"

    try {
        $output = mvn clean install -DskipTests 2>&1

        if ($LASTEXITCODE -eq 0) {
            Write-Host "   ✅ $service built successfully" -ForegroundColor Green
            $successCount++
        } else {
            Write-Host "   ❌ $service build FAILED" -ForegroundColor Red
            $failCount++
            $failedServices += $service
        }
    } catch {
        Write-Host "   ❌ $service build FAILED with error: $_" -ForegroundColor Red
        $failCount++
        $failedServices += $service
    } finally {
        Pop-Location
    }

    Write-Host ""
}

Write-Host "==================================================" -ForegroundColor Cyan
Write-Host "  Build Summary" -ForegroundColor Cyan
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host "✅ Successful: $successCount/$($services.Count)" -ForegroundColor Green
Write-Host "❌ Failed: $failCount/$($services.Count)" -ForegroundColor Red

if ($failCount -gt 0) {
    Write-Host ""
    Write-Host "Failed services:" -ForegroundColor Red
    foreach ($failed in $failedServices) {
        Write-Host "   - $failed" -ForegroundColor Red
    }
    Write-Host ""
    Write-Host "Please check the build logs above for errors." -ForegroundColor Yellow
} else {
    Write-Host ""
    Write-Host "🎉 All services built successfully!" -ForegroundColor Green
    Write-Host ""
    Write-Host "You can now run: .\start-all-services.ps1" -ForegroundColor Cyan
}

Write-Host ""

