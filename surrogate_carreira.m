% POLYNOMIAL REGRESSION MODEL WITH CROSS VALIDATION FOR AERODYNAMIC
% COEFFICIENT MAP OF FST10e

clear all
close all
clc

%% Import Raw Data

opts = detectImportOptions('Map_sims_ext.xlsm','Range','C5:AD121');
opts.VariableNames(1) = {'ID'};
opts.VariableNames(12) = {'Drag'};
opts.VariableNames(13) = {'SideF'};
opts.VariableNames(14) = {'DownF'};
opts.VariableNames(18) = {'Moment_Roll'};
opts.VariableNames(19) = {'Moment_Pitch'};
opts.VariableNames(20) = {'Moment_Yaw'};
opts.VariableNames(21) = {'Balance_Roll'};
opts.VariableNames(22) = {'Balance_Pitch'};
opts.SelectedVariableNames = opts.SelectedVariableNames([1, 3:10, 12:28]);
Data = readtable('D:\Transferências\Surrogate_carreira\Map_sims_ext.xlsm', opts);

clear opts;

Data.Steer_Avg = mean(Data{:,{'Steer_In','Steer_Out'}},2);

Model.x = Data{12:end,{'Fr_RH','Rr_RH','Yaw','Roll','Steer_Avg'}};
Model.y.CLA = Data{12:end,{'CzA'}};
Model.y.CDA = Data{12:end,{'CxA'}};
Model.y.Aero_Balance_Front = Data{12:end,{'Balance_Pitch'}};
Model.y.Aero_Balance_Out = Data{12:end,{'Balance_Roll'}};
Model.y.CYA = Data{12:end,{'CyA'}};
Model.y.CMzA = Data{12:end,{'CmzAb'}};


%% Surrogate Model

% Cross Validation leave-one-out
Ns = size(Model.x,1); 
MinOrder = 1;           %Minimum order to be considered for polynomial regression
MaxOrder = 3;           %Maximum order to be considered for polynomial regression
vars = fieldnames(Model.y);   


for v2 = 1:size(vars,1)             %For each variable of interest

cur_var = num2str(vars{v2}); 

for m = MinOrder:1:MaxOrder         %Cycle through possible order - For each order
    
    Ordername = ['Order_',num2str(m)];
    PRG.(cur_var).(Ordername).Overall.CV = zeros(Ns,1);
    
    for k = 1:Ns           %For each k training subset 
    
        %Training data - Removing test points from training dataset
        Xtrain = Model.x;
        Xtrain(k,:) = [];

        Ytrain = Model.y.(cur_var);
        Ytrain(k,:) = [];


        %Testing data - Only point removed from train
        Xtest = Model.x(k,:);
        Ytest = Model.y.(cur_var)(k,1);

        
        Subsetname = ['Subset_',num2str(k)];

        %Model fitting of each subset, with test data for CV 
        [PRG.(cur_var).(Ordername).(Subsetname)] = polyrg(Xtrain,Ytrain,m,Xtest,Ytest);

        %Allocate each subset error of test data into Overall vector
        PRG.(cur_var).(Ordername).Overall.CV(k,1) = PRG.(cur_var).(Ordername).(Subsetname).TestErrorYap;
    
    end
    
    %Model fitting for the entire sample size used for training and without test data 
    [PRG.(cur_var).(Ordername).Overall.FullSize] = polyrg(Model.x,Model.y.(cur_var),m);
    
    %Mean square error for CV comparison
    PRG.(cur_var).(Ordername).Overall.MSE = (sum((PRG.(cur_var).(Ordername).Overall.CV(:,1)).^2))/ Ns;
    PRG.(cur_var).MSE(m,1) = PRG.(cur_var).(Ordername).Overall.MSE;
end

[PRG.(cur_var).MinCV, PRG.(cur_var).BestOrder]= min (PRG.(cur_var).MSE);

BestOrdername = ['Order_',num2str(PRG.(cur_var).BestOrder)];
PRG.(cur_var).BestOr_OUT = PRG.(cur_var).(BestOrdername).Overall.FullSize;

end

%% MAP POINTS

RH_spac = 5; %[mm] x1-x2
Yaw_spac = 1.0; %[º] x3
Roll_spac = 0.1; %[º] x4   %Map 0.1
Steer_spac = 0.25; %[º] x5 %Map 0.25

yaw_cte = 0;

[x1, x2] = meshgrid(20:RH_spac:60, 20:RH_spac:80);
Map.X_RH_pts = [x1(:), x2(:)];

[x4, x5] = meshgrid(0:Roll_spac:2, 0:Steer_spac:12.5);
Map.X_Roll_Steer_pts = [x4(:), x5(:)];

for im = 1:size(Map.X_RH_pts,1)      %For each image

image_nr = ['image_',num2str(im)];

Map.X.(image_nr) = [repmat(x1(im),size(x4(:),1),1), repmat(x2(im),size(x4(:),1),1),...
    repmat(yaw_cte,size(x4(:),1),1), x4(:), x5(:)];

    for v2 = 1:size(vars,1)             %For each variable of interest  
        cur_var = num2str(vars{v2});

            for s = 1:size(Map.X.(image_nr),1)         %For each map point
                [Map.(cur_var).(image_nr)(s,1)] = prvalue(PRG.(cur_var).BestOr_OUT,Map.X.(image_nr)(s,:));
            end
        var_plot = [num2str(vars{v2}),'_plot'];    
        Map.(var_plot).(image_nr) = reshape(Map.(cur_var).(image_nr),size(x4,1),size(x5,2));    
            
    end
end

%Tile image plot
t = tiledlayout(size(x1,2),size(x1,1),'TileSpacing','none');

    for row = 1:size(x1,2)          %For every row
        for col = 1:size(x1,1)      %For every column
            
            im = size(Map.X_RH_pts,1) - size(x1,1)*row + col;
            image_nr = ['image_',num2str(im)];
            h(im) = nexttile;
            contourf(x4,x5,Map.CLA_plot.(image_nr),10,'LineWidth',0.45);        %20 levels of isolines
            h(im).FontName = 'Arial';
            h(im).FontWeight = 'bold';
            
             if (col == 1 && row == size(x1,2))
                 h(im).TickDir = 'out';
                 h(im).XTick = ([0 1 2]);
                 h(im).YTick = ([0 5 10]);
                 set(h(im),'XMinorTick','on');
                set(h(im),'YMinorTick','on');
                 
             else
                set(gca,'XTick',[], 'YTick', []);
             end
            
            
        end
    end
    
load('cb_carreira_v3.mat')    
cb = colorbar;
% set(h, 'Colormap', CB_cold_gr_hot, 'CLim', [2.6 3.6]); %Old version
% set(h, 'Colormap', CB_cold_gr_hot, 'CLim', [2.7 3.55]);  %Yaw 0
% set(h, 'Colormap', CB_cold_gr_hot, 'CLim', [2.25 2.85]); %YAW 15
set(h, 'Colormap', CB_cold_gr_hot, 'CLim', [2.3 3.5]);  %Yaw multiple

cb.Layout.Tile = 'east';
cb.Box =  'off';
cb.FontWeight = 'normal';
cb.FontSize = 10;
cb.TickDirection = 'out';
% t.XLabel.String = 'My x-Axis Label';
xlabel(t,'$\textrm{Rear ride height} \ [mm]$','Interpreter','latex','FontSize',20)
xlabel(t,'$\textrm{Yaw angle} \: [^{\circ}] \: - \: \psi$','FontSize',14)
ylabel(t,'$\textrm{Front ride height} \ [mm]$','Interpreter','latex','FontSize',20)
